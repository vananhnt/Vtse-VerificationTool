# Vtse-VerificationTool
Verification Tool using Symbolic Execution to verify some features of C/C++ programs

## Getting Started

To use invariant, pull from "invariant-new" branch
```
git clone "https://github.com/vananhnt/Vtse-VerificationTool.git"
git checkout "invariant-new"
```

### Prerequisites

In Windows, tools are put in "solvers" folder
In Linux, install mathsat, Z3 and Redlog using terminal

### Installing

- IDE: Intellj (recommend) or Eclipse
- Import as an Maven project (using pom.xml) or import as an Intellj project (using VTSE.iml)
- Add libraries in "libs" folder into the project

## Result
Reports from running benchmark are constantly updated on https://docs.google.com/spreadsheets/d/1b_pWn5cNFKQAFOcAcpIvEWUpW-kJgkTr8tM1AzL70yY/edit?usp=sharing

## Running the tests

2 benchmarks are currently used are floats-cdfpl and loop-acceleration, both are from sv-comp
```
https://github.com/sosy-lab/sv-benchmarks/tree/master/c/floats-cdfpl
https://github.com/sosy-lab/sv-benchmarks/tree/master/c/loop-acceleration
```
However, the tasks that we use have been reformatted and put in "./src/main/resources/benchmark/"

### How to verify loop-acceleration

```
  ExportExcel exportExcel = new ExportExcel();
  File file = new File(benchmarkPath);
  FileVerification fv = new FileVerification();
  //LoopTemplate.generateInvariantDirectory(file); // to generate invariant
  List<VerificationReport> reportList = fv.verifyDirectory(file, FunctionVerification.UNFOLD_MODE); //FunctionVerification.INVARIANT_MODE
  exportExcel.writeExcel(reportList);
```
Output is in the format of an excel report. 

### Some running examples

- See in package /src/main/java/com/vtse/runbenchmark/ for more details
- "RunInvariant" and "RunReachSafety" are used to run "loop-acc" and "cdfpl" respectively
- "VtseMain" are used as a front-end when running VTSE on terminal or cmd (just an example)

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

See the list of [contributors](https://github.com/vananhnt/Vtse-VerificationTool/graphs/contributors) who participated in this project.

## Acknowledgments

* First version (for verification of Java source code) belongs to batu4404
```
https://github.com/batu4404/VertificationTool
```
