(defn anyOperationFunction [f]
  (fn [& args]
    (fn [values]
      (apply f (map (fn [arg] (arg values)) args)))
    )
  )

(defn divideHelper ([x] (/ 1 (double x))) ([x & args] (/ x (double (apply * args)))))

(defn constant [cnst] (fn [values] cnst))
(defn variable [varb] (fn [values] (values varb)))
(def negate (anyOperationFunction -))

(def add (anyOperationFunction +))
(def subtract (anyOperationFunction -))
(def multiply (anyOperationFunction *))
(def divide (anyOperationFunction divideHelper))


(def arcTan (anyOperationFunction (fn [expr] (Math/atan expr))))
(def arcTan2 (anyOperationFunction #(Math/atan2 %1 %2)))



(def mapFunctionOperations
  {'+      add,
   '-      subtract,
   '*      multiply,
   '/      divide,
   'negate negate,
   'atan   arcTan,
   'atan2  arcTan2,
   'cnst  constant,
   'varb   variable
   }
  )





;-------------------------------------------------------------------------------
(defn proto-get
  "Returns object property respecting the prototype chain"
  ([obj key] (proto-get obj key nil))
  ([obj key default]
   (cond
     (contains? obj key) (obj key)
     (contains? obj :prototype) (proto-get (obj :prototype) key default)
     :else default)))

(defn proto-call
  "Calls object method respecting the prototype chain"
  [obj key & args]
  (apply (proto-get obj key) obj args))

(defn method
  "Creates method"
  [key] (fn [obj & args] (apply proto-call obj key args)))

(defn constructor
  "Defines constructor"
  [ctor prototype]
  (fn [& args] (apply ctor {:prototype prototype} args)))


;-------------------------------------------------------------------------------

(def evaluate (method :evaluate))
(def toString (method :toString))


(defn cnstOrVariableConstr [obj values]
  (assoc obj :x values))
(def Constant
  (constructor cnstOrVariableConstr
               {
                :evaluate (fn [obj values] (obj :x))
                :toString (fn [obj] (str (obj :x)))
                }))
(def Variable
  (constructor cnstOrVariableConstr
               {
                :evaluate (fn [obj values] (values (obj :x)))
                :toString (fn [obj] (obj :x))
                }))

(defn operationConstr [obj & operation]
  (assoc obj :operation operation))
(defn anyOperationObject [f sign]
  (constructor operationConstr
               {
                :evaluate (fn [obj values] (apply f (map (fn [exp] (evaluate exp values)) (obj :operation))))
                :toString (fn [obj] (str "(" sign " " (clojure.string/join " " (map toString (obj :operation))) ")"))
                }))

(def Negate (anyOperationObject - "negate"))
(def Add (anyOperationObject + "+"))
(def Subtract (anyOperationObject - "-"))
(def Multiply (anyOperationObject * "*"))
(def Divide (anyOperationObject divideHelper "/"))
(def Sinh (anyOperationObject (fn [args] (Math/sinh args)) "sinh"))
(def Cosh (anyOperationObject (fn [args] (Math/cosh args)) "cosh"))

(def mapObjectOperations
  {'+      Add,
   '-      Subtract,
   '*      Multiply,
   '/      Divide,
   'negate Negate,
   'cnst  Constant,
   'varb   Variable,
   'sinh Sinh,
   'cosh Cosh
   }
  )




(defn parse [expr getOperation]
  (cond
    (number? expr) ((getOperation 'cnst) expr)
    (list? expr) (apply (getOperation (first expr)) (mapv #(parse % getOperation) (pop expr)))
    :else ((getOperation 'varb) (str expr))))

(defn parseFunction [str]
  (parse (read-string str) mapFunctionOperations)
  )

(defn parseObject [str]
  (parse (read-string str) mapObjectOperations)
  )