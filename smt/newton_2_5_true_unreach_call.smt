(declare-fun IN_newton_2_5_true_unreach_call_0 () Real)
(declare-fun f_IN_newton_2_5_true_unreach_call_0 () Real)
(declare-fun f_IN_newton_2_5_true_unreach_call_1 () Real)
(declare-fun fp_IN_newton_2_5_true_unreach_call_0 () Real)
(declare-fun fp_IN_newton_2_5_true_unreach_call_1 () Real)
(declare-fun x_newton_2_5_true_unreach_call_0 () Real)
(declare-fun x_newton_2_5_true_unreach_call_1 () Real)
(declare-fun x_newton_2_5_true_unreach_call_2 () Real)
(declare-fun f_x1_newton_2_5_true_unreach_call_0 () Real)
(declare-fun f_x1_newton_2_5_true_unreach_call_1 () Real)
(declare-fun fp_x1_newton_2_5_true_unreach_call_0 () Real)
(declare-fun fp_x1_newton_2_5_true_unreach_call_1 () Real)
(declare-fun return_newton_2_5_true_unreach_call_0 () Real)
(assert (and (and (and (and (and (and (= f_IN_newton_2_5_true_unreach_call_1 (+ (+ (- IN_newton_2_5_true_unreach_call_0 (/ (* (* IN_newton_2_5_true_unreach_call_0 IN_newton_2_5_true_unreach_call_0) IN_newton_2_5_true_unreach_call_0) 6)) (/ (* (* (* (* IN_newton_2_5_true_unreach_call_0 IN_newton_2_5_true_unreach_call_0) IN_newton_2_5_true_unreach_call_0) IN_newton_2_5_true_unreach_call_0) IN_newton_2_5_true_unreach_call_0) 120)) (/ (* (* (* (* (* (* IN_newton_2_5_true_unreach_call_0 IN_newton_2_5_true_unreach_call_0) IN_newton_2_5_true_unreach_call_0) IN_newton_2_5_true_unreach_call_0) IN_newton_2_5_true_unreach_call_0) IN_newton_2_5_true_unreach_call_0) IN_newton_2_5_true_unreach_call_0) 5040))) (= fp_IN_newton_2_5_true_unreach_call_1 (+ (+ (- 1 (/ (* IN_newton_2_5_true_unreach_call_0 IN_newton_2_5_true_unreach_call_0) 2)) (/ (* (* (* IN_newton_2_5_true_unreach_call_0 IN_newton_2_5_true_unreach_call_0) IN_newton_2_5_true_unreach_call_0) IN_newton_2_5_true_unreach_call_0) 24)) (/ (* (* (* (* (* IN_newton_2_5_true_unreach_call_0 IN_newton_2_5_true_unreach_call_0) IN_newton_2_5_true_unreach_call_0) IN_newton_2_5_true_unreach_call_0) IN_newton_2_5_true_unreach_call_0) IN_newton_2_5_true_unreach_call_0) 720)))) (= x_newton_2_5_true_unreach_call_1 (- IN_newton_2_5_true_unreach_call_0 (/ f_IN_newton_2_5_true_unreach_call_1 fp_IN_newton_2_5_true_unreach_call_1)))) (= f_x1_newton_2_5_true_unreach_call_1 (+ (+ (- x_newton_2_5_true_unreach_call_1 (/ (* (* x_newton_2_5_true_unreach_call_1 x_newton_2_5_true_unreach_call_1) x_newton_2_5_true_unreach_call_1) 6)) (/ (* (* (* (* x_newton_2_5_true_unreach_call_1 x_newton_2_5_true_unreach_call_1) x_newton_2_5_true_unreach_call_1) x_newton_2_5_true_unreach_call_1) x_newton_2_5_true_unreach_call_1) 120)) (/ (* (* (* (* (* (* x_newton_2_5_true_unreach_call_1 x_newton_2_5_true_unreach_call_1) x_newton_2_5_true_unreach_call_1) x_newton_2_5_true_unreach_call_1) x_newton_2_5_true_unreach_call_1) x_newton_2_5_true_unreach_call_1) x_newton_2_5_true_unreach_call_1) 5040)))) (= fp_x1_newton_2_5_true_unreach_call_1 (+ (+ (- 1 (/ (* x_newton_2_5_true_unreach_call_1 x_newton_2_5_true_unreach_call_1) 2)) (/ (* (* (* x_newton_2_5_true_unreach_call_1 x_newton_2_5_true_unreach_call_1) x_newton_2_5_true_unreach_call_1) x_newton_2_5_true_unreach_call_1) 24)) (/ (* (* (* (* (* x_newton_2_5_true_unreach_call_1 x_newton_2_5_true_unreach_call_1) x_newton_2_5_true_unreach_call_1) x_newton_2_5_true_unreach_call_1) x_newton_2_5_true_unreach_call_1) x_newton_2_5_true_unreach_call_1) 720)))) (= x_newton_2_5_true_unreach_call_2 (- x_newton_2_5_true_unreach_call_1 (/ f_x1_newton_2_5_true_unreach_call_1 fp_x1_newton_2_5_true_unreach_call_1)))) (= return_newton_2_5_true_unreach_call_0 x_newton_2_5_true_unreach_call_2)))
(assert (and (> (+ IN_newton_2_5_true_unreach_call_0 1.0) 0) (< IN_newton_2_5_true_unreach_call_0 1.0)))
(assert (not (< return_newton_2_5_true_unreach_call_0 0.1)))
(check-sat)
(get-model)