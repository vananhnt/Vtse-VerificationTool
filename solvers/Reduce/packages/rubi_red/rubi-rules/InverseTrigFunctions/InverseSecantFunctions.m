(* ::Package:: *)

(* ::Title::Bold::Closed:: *)
(*\[Integral]ArcSec[a+b x]^n \[DifferentialD]x*)


(* ::Subsubsection:: *)
(*Reference: G&R 2.821.2, CRC 445', A&S 4.4.62'*)


(* ::Subsubsection:: *)
(*Derivation: Integration by parts*)


(* ::Subsubsection:: *)
(*Rule:*)


(* ::Subsubtitle::Bold:: *)
(*\[Integral]ArcSec[a+b x]\[DifferentialD]x  \[LongRightArrow]  (((a+b x) ArcSec[a+b x])/b)-\[Integral]1/((a+b x) Sqrt[1-1/(a+b x)^2]) \[DifferentialD]x*)


(* ::Subsubsection:: *)
(*Program code:*)


(* ::Code:: *)
Int[ArcSec[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*ArcSec[a+b*x]/b - 
  Int[1/((a+b*x)*Sqrt[1-1/(a+b*x)^2]),x] /;
FreeQ[{a,b},x]


(* ::PageBreak:: *)
(**)


(* ::Title::Bold::Closed:: *)
(*\[Integral]x^m ArcSec[a+b x]\[DifferentialD]x*)


(* ::Subsubsection:: *)
(*Derivation: Integration by substitution*)


(* ::Subsubsection:: *)
(*Rule: If  m\[Element]\[DoubleStruckCapitalZ] \[And] m>0, then*)


(* ::Subsubtitle::Bold:: *)
(*\[Integral]x^m ArcSec[a+b x]\[DifferentialD]x  \[LongRightArrow]  (1/b)Subst[\[Integral](-(a/b)+x/b)^m ArcSec[x]\[DifferentialD]x,x,a+b x]*)


(* ::Subsubsection:: *)
(*Program code:*)


(* ::Code:: *)
Int[x_^m_.*ArcSec[a_+b_.*x_],x_Symbol] :=
  Dist[1/b,Subst[Int[(-a/b+x/b)^m*ArcSec[x],x],x,a+b*x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m>0


(* ::Subsubsection:: *)
(**)


(* ::Subsubsection:: *)
(*Reference: CRC 474*)


(* ::Subsubsection:: *)
(*Derivation: Integration by parts*)


(* ::Subsubsection:: *)
(*Rule: If  m+1!=0, then*)


(* ::Subsubtitle::Bold:: *)
(*\[Integral]x^m ArcSec[a x]\[DifferentialD]x  \[LongRightArrow]  ((x^(m+1) ArcSec[a x])/(m+1))-1/(a(m+1)) \[Integral]x^(m-1)/Sqrt[1-1/(a^2 x^2)] \[DifferentialD]x*)


(* ::Subsubsection:: *)
(*Program code:*)


(* ::Code:: *)
Int[x_^m_.*ArcSec[a_.*x_],x_Symbol] :=
  x^(m+1)*ArcSec[a*x]/(m+1) - 
  Dist[1/(a*(m+1)),Int[x^(m-1)/Sqrt[1-1/(a*x)^2],x]] /;
FreeQ[{a,m},x] && NonzeroQ[m+1]


(* ::Subsubsection:: *)
(**)


(* ::Subsubsection:: *)
(*Reference: CRC 474*)


(* ::Subsubsection:: *)
(*Derivation: Integration by parts*)


(* ::Subsubsection:: *)
(*Rule: If  m+1!=0, then*)


(* ::Subsubtitle::Bold:: *)
(*\[Integral]x^m ArcSec[a+b x]\[DifferentialD]x  \[LongRightArrow]  ((x^(m+1) ArcSec[a+b x])/(m+1))-b/(m+1) \[Integral]x^(m+1)/((a+b x)^2 Sqrt[1-1/(a+b x)^2]) \[DifferentialD]x*)


(* ::Subsubsection:: *)
(*Program code:*)


(* ::Code:: *)
Int[x_^m_.*ArcSec[a_.+b_.*x_],x_Symbol] :=
  x^(m+1)*ArcSec[a+b*x]/(m+1) - 
  Dist[b/(m+1),Int[x^(m+1)/((a+b*x)^2*Sqrt[1-1/(a+b*x)^2]),x]] /;
FreeQ[{a,b,m},x] && NonzeroQ[m+1]


(* Int[ArcSec[a_.*x_^n_.]/x_,x_Symbol] :=
  I*ArcSec[a*x^n]^2/(2*n) - 
  ArcSec[a*x^n]*Log[1-1/(I/(x^n*a)+Sqrt[1-1/(x^(2*n)*a^2)])^2]/n + 
  I*PolyLog[2,1/(I/(x^n*a)+Sqrt[1-1/(x^(2*n)*a^2)])^2]/(2*n) /;
(* Sqrt[-1/a^2]*a*ArcCsc[a*x^n]^2/(2*n) + 
  Pi*Log[x]/2 - 
  Sqrt[-1/a^2]*a*ArcSinh[Sqrt[-1/a^2]/x^n]*Log[1-1/(Sqrt[-(1/a^2)]/x^n+Sqrt[1-1/(x^(2*n)*a^2)])^2]/n + 
  Sqrt[-1/a^2]*a*PolyLog[2, 1/(Sqrt[-1/a^2]/x^n+Sqrt[1-1/(x^(2*n)*a^2)])^2]/(2*n) *)
FreeQ[{a,n},x] *)


(* ::PageBreak:: *)
(**)


(* ::Title::Bold::Closed:: *)
(*\[Integral]u ArcSec[c/(a+b x^n)]^m \[DifferentialD]x*)


(* ::Subsubsection:: *)
(*Derivation:  Algebraic simplification*)


(* ::Subsubsection:: *)
(*Basis: ArcSec[z]=ArcCos[1/z]*)


(* ::Subsubsection:: *)
(*Rule:*)


(* ::Subsubtitle::Bold:: *)
(*\[Integral]u ArcSec[c/(a+b x^n)]^m \[DifferentialD]x  \[LongRightArrow]  \[Integral]u ArcCos[a/c+(b x^n)/c]^m \[DifferentialD]x*)


(* ::Subsubsection:: *)
(*Program code:*)


(* ::Code:: *)
Int[u_.*ArcSec[c_./(a_.+b_.*x_^n_.)]^m_.,x_Symbol] :=
  Int[u*ArcCos[a/c+b*x^n/c]^m,x] /;
FreeQ[{a,b,c,n,m},x]


(* ::PageBreak:: *)
(**)


(* ::Title::Bold::Closed:: *)
(*\[Integral]ArcSec[u]\[DifferentialD]x*)


(* ::Subsubsection:: *)
(*Derivation: Integration by parts*)


(* ::Subsubsection:: *)
(*Rule: If u is free of inverse functions, then*)


(* ::Subsubtitle::Bold:: *)
(*\[Integral]ArcSec[u]\[DifferentialD]x  \[LongRightArrow]  x ArcSec[u]-\[Integral](x \!\( *)
(*\*SubscriptBox[\(\[PartialD]\), \(x\)]u\))/(u^2 Sqrt[1-1/u^2]) \[DifferentialD]x*)


(* ::Subsubsection:: *)
(*Program code:*)


(* ::Code:: *)
Int[ArcSec[u_],x_Symbol] :=
  x*ArcSec[u] -
  Int[Regularize[x*D[u,x]/(u^2*Sqrt[1-1/u^2]),x],x] /;
InverseFunctionFreeQ[u,x] && Not[FunctionOfExponentialOfLinear[u,x]]
