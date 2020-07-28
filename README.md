# Vtse-VerificationTool
Verification Tool using Symbolic Execution to verify some features of C/C++ programs

## Getting Started

To use invariant, pull from master or "invariant-new" branch
```
git clone "https://github.com/vananhnt/Vtse-VerificationTool.git"
git checkout "invariant-new"
```

### Prerequisites

- In Windows, tools are already put in "solvers" folder, no installation required
- In Linux, install mathsat, Z3 and Redlog using terminal

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

### How to verify a function step-by-step (using unfolding)
Create AST of the whole program
```
ASTFactory ast = new ASTFactory(filePath);
```
Get user's assertions from xml file
```
List<AssertionMethod> listAssertion = AssertionMethod.getUserAssertions(PPFile);
\\one XML file can contain multiple user's assertion of multiple functions
```
Get pre-condtion and post-condition from AssertionMethod
```
AssertionMethod am; \\choose using am.getMethodName()
String pre = am.getPreCondition(); String post = am.getPostCondition();
```
Build CFG for a function
```
FunctionVerification function = ast.getListFunction().getFunction(functionName);
VtseCFG cfg = new VtseCFG(function, ast);
cfg.unfold(nLoops);
cfg.index();
```
Create SMTInput
```
SMTInput smtInput = new SMTInput(cfg.getVm().getVariableList(), cfg.createFormulas() );
```
Add pre and post condition into SMTInput (will reformat later)
```
String constraintTemp;
List<String> constraints = new ArrayList<>();
UserInput userInput = new UserInput();
ArrayList<Variable> params = cfg.getInitVariables();
params.add(new Variable(cfg.getTypeFunction(), "return"));
userInput.setParameter(params);
// add pre-condition
if (preCondition != null && !preCondition.equals("")) {
    constraintTemp = userInput.createUserAssertion(preCondition, cfg.getNameFunction());
    constraints.add(constraintTemp);
 }
// add user's assertion
constraintTemp = userInput.createUserAssertion(postCondition,cfg.getNameFunction());
constraintTemp = "(not " + constraintTemp + ")";
constraints.add(constraintTemp);
smtInput.setConstrainst(constraints);
```
Print SMTInput into File
```
smtInput.printInputToOutputStreamAssert(fo);
```
Input SMTInput into Z3 and generate Report
```
Report report = new Report();
...
VerificationReport verReport = report.generateReport(result);
```
* For more details, check .\src\main\java\com\vtse\app\verification\FunctionVerification.java

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
