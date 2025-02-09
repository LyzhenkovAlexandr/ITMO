(defn opWithVectorsOrMatrix [f]
  (fn [& elements]
    (apply mapv f elements))
  )

(defn op [f]
  (fn [arg1 arg2] (mapv (fn [x] (f x arg2)) arg1))
  )

(def v+ (opWithVectorsOrMatrix +))
(def v- (opWithVectorsOrMatrix -))
(def v* (opWithVectorsOrMatrix *))
(def vd (opWithVectorsOrMatrix /))

(defn scalar [vector1 vector2]
  (apply + (v* vector1 vector2))
  )

(def v*s (op *))

(defn vect [vec1 vec2]
  (vector (- (* (nth vec1 1) (nth vec2 2)) (* (nth vec2 1) (nth vec1 2)))
          (- (* (first vec2) (nth vec1 2)) (* (first vec1) (nth vec2 2)))
          (- (* (first vec1) (nth vec2 1)) (* (first vec2) (nth vec1 1))))
  )


(defn transpose [matrix]
  (apply mapv vector matrix)
  )

(def m+ (opWithVectorsOrMatrix v+))
(def m- (opWithVectorsOrMatrix v-))
(def m* (opWithVectorsOrMatrix v*))
(def md (opWithVectorsOrMatrix vd))

(def m*s (op v*s))
(def m*v (op scalar))

(defn m*m [matrix1 matrix2]
  (mapv (fn [y] (mapv (fn [x] (scalar y x)) (transpose matrix2))) matrix1)
  )


(defn s [f]
  (fn [& args]
    (cond
      (number? (first args)) (apply f args)
      :else (apply mapv (s f) args)))
  )


(def s+ (s +))
(def s- (s -))
(def s* (s *))
(def sd (s /))