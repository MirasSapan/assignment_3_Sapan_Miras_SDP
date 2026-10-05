# Assignment #3 — Bridge Pattern: Report Exporter

**Course:** Software Design Patterns  
**Topic:** University report exporter — the same report (grades, attendance) can be exported to different formats (plain text, Markdown, HTML).

## The problem Bridge solves here

Without Bridge we would need a class for every combination:
`GradeTextReport`, `GradeMarkdownReport`, `GradeHtmlReport`, `AttendanceTextReport`, ...
→ 2 reports × 3 formats = **6 classes**, and every new report or format multiplies the count.

With Bridge the two dimensions vary independently:
**2 reports + 3 formatters = 5 classes**, and any report works with any format.

## Structure

| Bridge role | Class | Package |
|---|---|---|
| Abstraction | `Report` (abstract class, holds `ReportFormatter`) | `report` |
| Refined Abstraction | `GradeReport`, `AttendanceReport` | `report` |
| Implementor | `ReportFormatter` (interface) | `format` |
| Concrete Implementor | `PlainTextFormatter`, `MarkdownFormatter`, `HtmlFormatter` | `format` |
| Client | `Main` | root |
| Domain data | `StudentGrade`, `AttendanceRecord` (records) | `model` |

```
        Report  ───────────── bridge ─────────────▶  «interface» ReportFormatter
   (Abstraction)                                         (Implementor)
   + generate()                                      + title() + tableHeader()
        ▲                                            + tableRow() + tableEnd()
   ┌────┴──────────┐                                 + summary() + document()
GradeReport  AttendanceReport                              ▲
                                       ┌───────────────────┼──────────────────┐
                              PlainTextFormatter   MarkdownFormatter   HtmlFormatter
```

* **Abstraction side** (`Report` + subclasses) decides **WHAT** the report contains: title, columns, rows, summary, business rules (passing score, minimum attendance).
* **Implementor side** (`ReportFormatter` + implementations) decides **HOW** it is drawn, using only low-level primitives (title, header, row, summary, document wrapper).
* `Report.generate()` is the high-level operation that is built entirely from implementor primitives.
* The client (`Main`) composes any report with any formatter at runtime and loops over the formatters — the report classes are never changed.

## How to run

Requires JDK 17+.

```bash
javac -d out $(find src -name "*.java")
java -cp out kz.sdp.bridge.Main
```

Or open the project in IntelliJ IDEA (mark `src/main/java` as Sources Root) and run `Main`.

### Sample output (fragment)

```
----- GradeReport + MarkdownFormatter -----
# Student Grade Report

| Student | Course | Score | Status |
| --- | --- | --- | --- |
| Aruzhan | Java OOP | 91.5 | PASSED |
| Dias | Java OOP | 47.0 | FAILED |
| Madina | Java OOP | 78.0 | PASSED |

**Average score: 72.2**
```

## Clean Code principles applied

1. **Clear separation of abstraction vs. implementation responsibilities.**
   `Report` subclasses only prepare data (`title()`, `columns()`, `rows()`, `summary()`); they never contain `<td>`, `|` or padding. Formatters never know what "grade" or "attendance" means. The formatter field in `Report` is `private`, so subclasses and the client cannot reach into implementor details — they only call `generate()`.

2. **Meaningful, role-revealing names.**
   `Report` / `GradeReport` / `AttendanceReport` on the abstraction side, `ReportFormatter` / `*Formatter` on the implementor side — the suffix itself tells which side of the bridge a class belongs to. Methods are verbs/nouns of the domain: `isPassed`, `hasLowAttendance`, `averageScore`, `tableRow`. Magic numbers are named constants: `PASSING_SCORE`, `MIN_ATTENDANCE_PERCENT`, `COLUMN_WIDTH`.

3. **Small, focused classes (Single Responsibility Principle).**
   Each class has one reason to change: `HtmlFormatter` changes only if HTML layout changes, `GradeReport` only if grading rules change, records only hold validated data. No class is longer than ~60 lines; methods are a few lines each.

4. **No duplicated logic (DRY).**
   The report-building algorithm exists in exactly one place — `Report.generate()` (declared `final`, so it can't be copied or broken in subclasses). Shared formatting of numbers is in `Report.formatNumber()`. Inside each formatter repeated row logic is extracted into private helpers (`toFixedWidthLine`, `toMarkdownRow`, `toHtmlRow`).

5. **Open/Closed & backward-compatible design.**
   Adding a new format (e.g. `CsvFormatter`) = one new class implementing `ReportFormatter`; no change to `Report` or its subclasses. Adding a new report (e.g. `ScholarshipReport`) = one new subclass of `Report`; no change to any formatter.

6. **Program to an interface, depend on abstractions (DIP).**
   `Report` depends on the `ReportFormatter` interface, not on concrete classes; the concrete formatter is injected through the constructor (composition over inheritance).

7. **Immutability and fail-fast validation.**
   Data classes are Java `record`s that validate their input in compact constructors; lists are defensively copied with `List.copyOf`; `Report` rejects a `null` formatter immediately.

## Extending the project (example)

```java
public class CsvFormatter implements ReportFormatter {
    public String title(String text)              { return "# " + text + "\n"; }
    public String tableHeader(List<String> cols)  { return String.join(",", cols) + "\n"; }
    public String tableRow(List<String> cells)    { return String.join(",", cells) + "\n"; }
    public String tableEnd()                      { return ""; }
    public String summary(String text)            { return "# " + text + "\n"; }
    public String document(String body)           { return body; }
}
// new GradeReport(new CsvFormatter(), grades).generate();  — Report classes untouched
```
