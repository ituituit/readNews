package com.cheesemobile.util;

import com.cheesemobile.domain.Point;

public final class Vector3f implements Cloneable, java.io.Serializable {

	public final static Vector3f ZERO = new Vector3f(0, 0, 0);
	public final static Vector3f NAN = new Vector3f(Float.NaN, Float.NaN,
			Float.NaN);
	public final static Vector3f UNIT_X = new Vector3f(1, 0, 0);
	public final static Vector3f UNIT_Y = new Vector3f(0, 1, 0);
	public final static Vector3f UNIT_Z = new Vector3f(0, 0, 1);
	public final static Vector3f UNIT_XYZ = new Vector3f(1, 1, 1);
	public final static Vector3f POSITIVE_INFINITY = new Vector3f(
			Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY,
			Float.POSITIVE_INFINITY);
	public final static Vector3f NEGATIVE_INFINITY = new Vector3f(
			Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY,
			Float.NEGATIVE_INFINITY);

	/**
	 * the x value of the vector.
	 */
	public float x;

	/**
	 * the y value of the vector.
	 */
	public float y;

	/**
	 * the z value of the vector.
	 */
	public float z;

	/**
	 * Constructor instantiates a new <code>Vector3f</code> with default values
	 * of (0,0,0).
	 */
	public Vector3f() {
		x = y = z = 0;
	}

	/**
	 * Constructor instantiates a new <code>Vector3f</code> with provides
	 * values.
	 * 
	 * @param x
	 *            the x value of the vector.
	 * @param y
	 *            the y value of the vector.
	 * @param z
	 *            the z value of the vector.
	 */
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * <code>set</code> sets the x,y,z values of the vector based on passed
	 * parameters.
	 * 
	 * @param x
	 *            the x value of the vector.
	 * @param y
	 *            the y value of the vector.
	 * @param z
	 *            the z value of the vector.
	 * @return this vector
	 */
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/**
	 * <code>set</code> sets the x,y,z values of the vector by copying the
	 * supplied vector.
	 * 
	 * @param vect
	 *            the vector to copy.
	 * @return this vector
	 */
	public Vector3f set(Vector3f vect) {
		this.x = vect.x;
		this.y = vect.y;
		this.z = vect.z;
		return this;
	}

	/**
	 * <code>add</code> adds a provided vector to this vector creating a
	 * resultant vector which is returned. If the provided vector is null, null
	 * is returned.
	 * 
	 * @param vec
	 *            the vector to add to this.
	 * @return the resultant vector.
	 */
	public Vector3f add(Vector3f vec) {
		if (null == vec) {
			return null;
		}
		return new Vector3f(x + vec.x, y + vec.y, z + vec.z);
	}

	/**
	 * <code>add</code> adds the values of a provided vector storing the values
	 * in the supplied vector.
	 * 
	 * @param vec
	 *            the vector to add to this
	 * @param result
	 *            the vector to store the result in
	 * @return result returns the supplied result vector.
	 */
	public Vector3f add(Vector3f vec, Vector3f result) {
		result.x = x + vec.x;
		result.y = y + vec.y;
		result.z = z + vec.z;
		return result;
	}

	/**
	 * <code>addLocal</code> adds a provided vector to this vector internally,
	 * and returns a handle to this vector for easy chaining of calls. If the
	 * provided vector is null, null is returned.
	 * 
	 * @param vec
	 *            the vector to add to this vector.
	 * @return this
	 */
	public Vector3f addLocal(Vector3f vec) {
		if (null == vec) {
			return null;
		}
		x += vec.x;
		y += vec.y;
		z += vec.z;
		return this;
	}

	/**
	 * <code>add</code> adds the provided values to this vector, creating a new
	 * vector that is then returned.
	 * 
	 * @param addX
	 *            the x value to add.
	 * @param addY
	 *            the y value to add.
	 * @param addZ
	 *            the z value to add.
	 * @return the result vector.
	 */
	public Vector3f add(float addX, float addY, float addZ) {
		return new Vector3f(x + addX, y + addY, z + addZ);
	}

	/**
	 * <code>addLocal</code> adds the provided values to this vector internally,
	 * and returns a handle to this vector for easy chaining of calls.
	 * 
	 * @param addX
	 *            value to add to x
	 * @param addY
	 *            value to add to y
	 * @param addZ
	 *            value to add to z
	 * @return this
	 */
	public Vector3f addLocal(float addX, float addY, float addZ) {
		x += addX;
		y += addY;
		z += addZ;
		return this;
	}

	/**
	 * <code>scaleAdd</code> multiplies this vector by a scalar then adds the
	 * given Vector3f.
	 * 
	 * @param scalar
	 *            the value to multiply this vector by.
	 * @param add
	 *            the value to add
	 */
	public Vector3f scaleAdd(float scalar, Vector3f add) {
		x = x * scalar + add.x;
		y = y * scalar + add.y;
		z = z * scalar + add.z;
		return this;
	}

	/**
	 * <code>scaleAdd</code> multiplies the given vector by a scalar then adds
	 * the given vector.
	 * 
	 * @param scalar
	 *            the value to multiply this vector by.
	 * @param mult
	 *            the value to multiply the scalar by
	 * @param add
	 *            the value to add
	 */
	public Vector3f scaleAdd(float scalar, Vector3f mult, Vector3f add) {
		this.x = mult.x * scalar + add.x;
		this.y = mult.y * scalar + add.y;
		this.z = mult.z * scalar + add.z;
		return this;
	}

	/**
	 * <code>dot</code> calculates the dot product of this vector with a
	 * provided vector. If the provided vector is null, 0 is returned.
	 * 
	 * @param vec
	 *            the vector to dot with this vector.
	 * @return the resultant dot product of this vector and a given vector.
	 */
	public float dot(Vector3f vec) {
		if (null == vec) {
			return 0;
		}
		return x * vec.x + y * vec.y + z * vec.z;
	}

	/**
	 * <code>cross</code> calculates the cross product of this vector with a
	 * parameter vector v.
	 * 
	 * @param v
	 *            the vector to take the cross product of with this.
	 * @return the cross product vector.
	 */
	public Vector3f cross(Vector3f v) {
		return cross(v, null);
	}

	/**
	 * <code>cross</code> calculates the cross product of this vector with a
	 * parameter vector v. The result is stored in <code>result</code>
	 * 
	 * @param v
	 *            the vector to take the cross product of with this.
	 * @param result
	 *            the vector to store the cross product result.
	 * @return result, after recieving the cross product vector.
	 */
	public Vector3f cross(Vector3f v, Vector3f result) {
		return cross(v.x, v.y, v.z, result);
	}

	/**
	 * <code>cross</code> calculates the cross product of this vector with a
	 * parameter vector v. The result is stored in <code>result</code>
	 * 
	 * @param otherX
	 *            x component of the vector to take the cross product of with
	 *            this.
	 * @param otherY
	 *            y component of the vector to take the cross product of with
	 *            this.
	 * @param otherZ
	 *            z component of the vector to take the cross product of with
	 *            this.
	 * @param result
	 *            the vector to store the cross product result.
	 * @return result, after recieving the cross product vector.
	 */
	public Vector3f cross(float otherX, float otherY, float otherZ,
			Vector3f result) {
		if (result == null)
			result = new Vector3f();
		float resX = ((y * otherZ) - (z * otherY));
		float resY = ((z * otherX) - (x * otherZ));
		float resZ = ((x * otherY) - (y * otherX));
		result.set(resX, resY, resZ);
		return result;
	}

	/**
	 * <code>crossLocal</code> calculates the cross product of this vector with
	 * a parameter vector v.
	 * 
	 * @param v
	 *            the vector to take the cross product of with this.
	 * @return this.
	 */
	public Vector3f crossLocal(Vector3f v) {
		return crossLocal(v.x, v.y, v.z);
	}

	/**
	 * <code>crossLocal</code> calculates the cross product of this vector with
	 * a parameter vector v.
	 * 
	 * @param otherX
	 *            x component of the vector to take the cross product of with
	 *            this.
	 * @param otherY
	 *            y component of the vector to take the cross product of with
	 *            this.
	 * @param otherZ
	 *            z component of the vector to take the cross product of with
	 *            this.
	 * @return this.
	 */
	public Vector3f crossLocal(float otherX, float otherY, float otherZ) {
		float tempx = (y * otherZ) - (z * otherY);
		float tempy = (z * otherX) - (x * otherZ);
		z = (x * otherY) - (y * otherX);
		x = tempx;
		y = tempy;
		return this;
	}

	/**
	 * <code>lengthSquared</code> calculates the squared value of the magnitude
	 * of the vector.
	 * 
	 * @return the magnitude squared of the vector.
	 */
	public float lengthSquared() {
		return x * x + y * y + z * z;
	}

	/**
	 * <code>distanceSquared</code> calculates the distance squared between this
	 * vector and vector v.
	 * 
	 * @param v
	 *            the second vector to determine the distance squared.
	 * @return the distance squared between the two vectors.
	 */
	public double distanceSquared(Vector3f v) {
		double dx = x - v.x;
		double dy = y - v.y;
		double dz = z - v.z;
		return dx * dx + dy * dy + dz * dz;
	}

	/**
	 * <code>distance</code> calculates the distance between this vector and
	 * vector v.
	 * 
	 * @param v
	 *            the second vector to determine the distance.
	 * @return the distance between the two vectors.
	 */
	public double distance(Vector3f v) {
		return Math.sqrt(distanceSquared(v));
	}

	/**
	 * <code>mult</code> multiplies this vector by a scalar. The resultant
	 * vector is returned.
	 * 
	 * @param scalar
	 *            the value to multiply this vector by.
	 * @return the new vector.
	 */
	public Vector3f mult(float scalar) {
		return new Vector3f(x * scalar, y * scalar, z * scalar);
	}

	/**
	 * <code>mult</code> multiplies this vector by a scalar. The resultant
	 * vector is supplied as the second parameter and returned.
	 * 
	 * @param scalar
	 *            the scalar to multiply this vector by.
	 * @param product
	 *            the product to store the result in.
	 * @return product
	 */
	public Vector3f mult(float scalar, Vector3f product) {
		if (null == product) {
			product = new Vector3f();
		}

		product.x = x * scalar;
		product.y = y * scalar;
		product.z = z * scalar;
		return product;
	}

	/**
	 * <code>multLocal</code> multiplies this vector by a scalar internally, and
	 * returns a handle to this vector for easy chaining of calls.
	 * 
	 * @param scalar
	 *            the value to multiply this vector by.
	 * @return this
	 */
	public Vector3f multLocal(float scalar) {
		x *= scalar;
		y *= scalar;
		z *= scalar;
		return this;
	}

	/**
	 * <code>multLocal</code> multiplies a provided vector to this vector
	 * internally, and returns a handle to this vector for easy chaining of
	 * calls. If the provided vector is null, null is returned.
	 * 
	 * @param vec
	 *            the vector to mult to this vector.
	 * @return this
	 */
	public Vector3f multLocal(Vector3f vec) {
		if (null == vec) {
			return null;
		}
		x *= vec.x;
		y *= vec.y;
		z *= vec.z;
		return this;
	}

	/**
	 * <code>multLocal</code> multiplies this vector by 3 scalars internally,
	 * and returns a handle to this vector for easy chaining of calls.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return this
	 */
	public Vector3f multLocal(float x, float y, float z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}

	/**
	 * <code>multLocal</code> multiplies a provided vector to this vector
	 * internally, and returns a handle to this vector for easy chaining of
	 * calls. If the provided vector is null, null is returned.
	 * 
	 * @param vec
	 *            the vector to mult to this vector.
	 * @return this
	 */
	public Vector3f mult(Vector3f vec) {
		if (null == vec) {
			return null;
		}
		return mult(vec, null);
	}

	/**
	 * <code>multLocal</code> multiplies a provided vector to this vector
	 * internally, and returns a handle to this vector for easy chaining of
	 * calls. If the provided vector is null, null is returned.
	 * 
	 * @param vec
	 *            the vector to mult to this vector.
	 * @param store
	 *            result vector (null to create a new vector)
	 * @return this
	 */
	public Vector3f mult(Vector3f vec, Vector3f store) {
		if (null == vec) {
			return null;
		}
		if (store == null)
			store = new Vector3f();
		return store.set(x * vec.x, y * vec.y, z * vec.z);
	}

	/**
	 * <code>divide</code> divides the values of this vector by a scalar and
	 * returns the result. The values of this vector remain untouched.
	 * 
	 * @param scalar
	 *            the value to divide this vectors attributes by.
	 * @return the result <code>Vector</code>.
	 */
	public Vector3f divide(float scalar) {
		scalar = 1f / scalar;
		return new Vector3f(x * scalar, y * scalar, z * scalar);
	}

	/**
	 * <code>divideLocal</code> divides this vector by a scalar internally, and
	 * returns a handle to this vector for easy chaining of calls. Dividing by
	 * zero will result in an exception.
	 * 
	 * @param scalar
	 *            the value to divides this vector by.
	 * @return this
	 */
	public Vector3f divideLocal(float scalar) {
		scalar = 1f / scalar;
		x *= scalar;
		y *= scalar;
		z *= scalar;
		return this;
	}

	/**
	 * <code>divide</code> divides the values of this vector by a scalar and
	 * returns the result. The values of this vector remain untouched.
	 * 
	 * @param scalar
	 *            the value to divide this vectors attributes by.
	 * @return the result <code>Vector</code>.
	 */
	public Vector3f divide(Vector3f scalar) {
		return new Vector3f(x / scalar.x, y / scalar.y, z / scalar.z);
	}

	/**
	 * <code>divideLocal</code> divides this vector by a scalar internally, and
	 * returns a handle to this vector for easy chaining of calls. Dividing by
	 * zero will result in an exception.
	 * 
	 * @param scalar
	 *            the value to divides this vector by.
	 * @return this
	 */
	public Vector3f divideLocal(Vector3f scalar) {
		x /= scalar.x;
		y /= scalar.y;
		z /= scalar.z;
		return this;
	}

	/**
	 * <code>negate</code> returns the negative of this vector. All values are
	 * negated and set to a new vector.
	 * 
	 * @return the negated vector.
	 */
	public Vector3f negate() {
		return new Vector3f(-x, -y, -z);
	}

	/**
	 * <code>negateLocal</code> negates the internal values of this vector.
	 * 
	 * @return this.
	 */
	public Vector3f negateLocal() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}

	/**
	 * <code>subtract</code> subtracts the values of a given vector from those
	 * of this vector creating a new vector object. If the provided vector is
	 * null, null is returned.
	 * 
	 * @param vec
	 *            the vector to subtract from this vector.
	 * @return the result vector.
	 */
	public Vector3f subtract(Vector3f vec) {
		return new Vector3f(x - vec.x, y - vec.y, z - vec.z);
	}

	/**
	 * <code>subtractLocal</code> subtracts a provided vector to this vector
	 * internally, and returns a handle to this vector for easy chaining of
	 * calls. If the provided vector is null, null is returned.
	 * 
	 * @param vec
	 *            the vector to subtract
	 * @return this
	 */
	public Vector3f subtractLocal(Vector3f vec) {
		if (null == vec) {
			return null;
		}
		x -= vec.x;
		y -= vec.y;
		z -= vec.z;
		return this;
	}

	/**
	 * <code>subtract</code>
	 * 
	 * @param vec
	 *            the vector to subtract from this
	 * @param result
	 *            the vector to store the result in
	 * @return result
	 */
	public Vector3f subtract(Vector3f vec, Vector3f result) {
		if (result == null) {
			result = new Vector3f();
		}
		result.x = x - vec.x;
		result.y = y - vec.y;
		result.z = z - vec.z;
		return result;
	}

	/**
	 * <code>subtract</code> subtracts the provided values from this vector,
	 * creating a new vector that is then returned.
	 * 
	 * @param subtractX
	 *            the x value to subtract.
	 * @param subtractY
	 *            the y value to subtract.
	 * @param subtractZ
	 *            the z value to subtract.
	 * @return the result vector.
	 */
	public Vector3f subtract(float subtractX, float subtractY, float subtractZ) {
		return new Vector3f(x - subtractX, y - subtractY, z - subtractZ);
	}

	/**
	 * <code>subtractLocal</code> subtracts the provided values from this vector
	 * internally, and returns a handle to this vector for easy chaining of
	 * calls.
	 * 
	 * @param subtractX
	 *            the x value to subtract.
	 * @param subtractY
	 *            the y value to subtract.
	 * @param subtractZ
	 *            the z value to subtract.
	 * @return this
	 */
	public Vector3f subtractLocal(float subtractX, float subtractY,
			float subtractZ) {
		x -= subtractX;
		y -= subtractY;
		z -= subtractZ;
		return this;
	}

	/**
	 * <code>normalize</code> returns the unit vector of this vector.
	 * 
	 * @return unit vector of this vector.
	 */
	public Vector3f normalize() {
		// float length = length();
		// if (length != 0) {
		// return divide(length);
		// }
		//
		// return divide(1);
		double length = x * x + y * y + z * z;
		if (length != 1f && length != 0f) {
			length = 1.0f / Math.sqrt(length);
			return new Vector3f((float) (x * length), (float) (y * length),
					(float) (z * length));
		}
		return clone();
	}

	/**
	 * <code>normalizeLocal</code> makes this vector into a unit vector of
	 * itself.
	 * 
	 * @return this.
	 */
	public Vector3f normalizeLocal() {
		// NOTE: this implementation is more optimized
		// than the old jme normalize as this method
		// is commonly used.
		double length = x * x + y * y + z * z;
		if (length != 1f && length != 0f) {
			length = 1.0f / Math.sqrt(length);
			x *= length;
			y *= length;
			z *= length;
		}
		return this;
	}

	/**
	 * <code>maxLocal</code> computes the maximum value for each component in
	 * this and <code>other</code> vector. The result is stored in this vector.
	 * 
	 * @param other
	 */
	public void maxLocal(Vector3f other) {
		x = other.x > x ? other.x : x;
		y = other.y > y ? other.y : y;
		z = other.z > z ? other.z : z;
	}

	/**
	 * <code>minLocal</code> computes the minimum value for each component in
	 * this and <code>other</code> vector. The result is stored in this vector.
	 * 
	 * @param other
	 */
	public void minLocal(Vector3f other) {
		x = other.x < x ? other.x : x;
		y = other.y < y ? other.y : y;
		z = other.z < z ? other.z : z;
	}

	/**
	 * <code>zero</code> resets this vector's data to zero internally.
	 */
	public Vector3f zero() {
		x = y = z = 0;
		return this;
	}

	/**
	 * Sets this vector to the interpolation by changeAmnt from this to the
	 * finalVec this=(1-changeAmnt)*this + changeAmnt * finalVec
	 * 
	 * @param finalVec
	 *            The final vector to interpolate towards
	 * @param changeAmnt
	 *            An amount between 0.0 - 1.0 representing a precentage change
	 *            from this towards finalVec
	 */
	public Vector3f interpolate(Vector3f finalVec, float changeAmnt) {
		this.x = (1 - changeAmnt) * this.x + changeAmnt * finalVec.x;
		this.y = (1 - changeAmnt) * this.y + changeAmnt * finalVec.y;
		this.z = (1 - changeAmnt) * this.z + changeAmnt * finalVec.z;
		return this;
	}

	/**
	 * Sets this vector to the interpolation by changeAmnt from beginVec to
	 * finalVec this=(1-changeAmnt)*beginVec + changeAmnt * finalVec
	 * 
	 * @param beginVec
	 *            the beging vector (changeAmnt=0)
	 * @param finalVec
	 *            The final vector to interpolate towards
	 * @param changeAmnt
	 *            An amount between 0.0 - 1.0 representing a precentage change
	 *            from beginVec towards finalVec
	 */
	public Vector3f interpolate(Vector3f beginVec, Vector3f finalVec,
			float changeAmnt) {
		this.x = (1 - changeAmnt) * beginVec.x + changeAmnt * finalVec.x;
		this.y = (1 - changeAmnt) * beginVec.y + changeAmnt * finalVec.y;
		this.z = (1 - changeAmnt) * beginVec.z + changeAmnt * finalVec.z;
		return this;
	}

	/**
	 * Check a vector... if it is null or its floats are NaN or infinite, return
	 * false. Else return true.
	 * 
	 * @param vector
	 *            the vector to check
	 * @return true or false as stated above.
	 */
	public static boolean isValidVector(Vector3f vector) {
		if (vector == null)
			return false;
		if (Float.isNaN(vector.x) || Float.isNaN(vector.y)
				|| Float.isNaN(vector.z))
			return false;
		if (Float.isInfinite(vector.x) || Float.isInfinite(vector.y)
				|| Float.isInfinite(vector.z))
			return false;
		return true;
	}

	@Override
	public Vector3f clone() {
		try {
			return (Vector3f) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(); // can not happen
		}
	}

	/**
	 * Saves this Vector3f into the given float[] object.
	 * 
	 * @param floats
	 *            The float[] to take this Vector3f. If null, a new float[3] is
	 *            created.
	 * @return The array, with X, Y, Z float values in that order
	 */
	public float[] toArray(float[] floats) {
		if (floats == null) {
			floats = new float[3];
		}
		floats[0] = x;
		floats[1] = y;
		floats[2] = z;
		return floats;
	}

	/**
	 * are these two vectors the same? they are is they both have the same x,y,
	 * and z values.
	 * 
	 * @param o
	 *            the object to compare for equality
	 * @return true if they are equal
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Vector3f)) {
			return false;
		}

		if (this == o) {
			return true;
		}

		Vector3f comp = (Vector3f) o;
		if (Float.compare(x, comp.x) != 0)
			return false;
		if (Float.compare(y, comp.y) != 0)
			return false;
		if (Float.compare(z, comp.z) != 0)
			return false;
		return true;
	}

	/**
	 * <code>hashCode</code> returns a unique code for this vector object based
	 * on it's values. If two vectors are logically equivalent, they will return
	 * the same hash code value.
	 * 
	 * @return the hash code value of this vector.
	 */
	public int hashCode() {
		int hash = 37;
		hash += 37 * hash + Float.floatToIntBits(x);
		hash += 37 * hash + Float.floatToIntBits(y);
		hash += 37 * hash + Float.floatToIntBits(z);
		return hash;
	}

	/**
	 * <code>toString</code> returns the string representation of this vector.
	 * The format is:
	 * <p/>
	 * org.jme.math.Vector3f [X=XX.XXXX, Y=YY.YYYY, Z=ZZ.ZZZZ]
	 * 
	 * @return the string representation of this vector.
	 */
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

	public float getX() {
		return x;
	}

	public Vector3f setX(float x) {
		this.x = x;
		return this;
	}

	public float getY() {
		return y;
	}

	public Vector3f setY(float y) {
		this.y = y;
		return this;
	}

	public float getZ() {
		return z;
	}

	public Vector3f setZ(float z) {
		this.z = z;
		return this;
	}

	/**
	 * @param index
	 * @return x value if index == 0, y value if index == 1 or z value if index
	 *         == 2
	 * @throws IllegalArgumentException
	 *             if index is not one of 0, 1, 2.
	 */
	public float get(int index) {
		switch (index) {
		case 0:
			return x;
		case 1:
			return y;
		case 2:
			return z;
		}
		throw new IllegalArgumentException("index must be either 0, 1 or 2");
	}

	/**
	 * @param index
	 *            which field index in this vector to set.
	 * @param value
	 *            to set to one of x, y or z.
	 * @throws IllegalArgumentException
	 *             if index is not one of 0, 1, 2.
	 */
	public void set(int index, float value) {
		switch (index) {
		case 0:
			x = value;
			return;
		case 1:
			y = value;
			return;
		case 2:
			z = value;
			return;
		}
		throw new IllegalArgumentException("index must be either 0, 1 or 2");
	}

	public static float[] calculateDistance(Point point, Point linea, Point lineb) {
		return calculateDistance(new Vector3f(linea.x,
				linea.y, 0), new Vector3f(lineb.x, lineb.y, 0),new Vector3f(point.x, point.y, 0));
	}

	private static float[] calculateDistance(Vector3f A, Vector3f B, Vector3f C) {
		float[] dxy = new float[3];
		// 计算点到直线的距离
		Vector3f abDir = A.subtract(B).normalize(); // AB
		Vector3f cb = C.subtract(B);
		float length = abDir.dot(cb); // cb在ab上的投影
		Vector3f result = abDir.mult(length).add(B); // 计算出垂直交点
//		System.out.println("ths cross point :" + result);
		dxy[1] = result.x;
		dxy[2] = result.y;
		float distance_a = (float) C.distance(A);
		float distance_b = (float) C.distance(B);
		float distance_result = (float) C.distance(result);

		float min = Math.min(distance_a, distance_b);
		float moreMin = Math.min(min, distance_result);

		Vector3f ar = A.subtractLocal(result);
		Vector3f br = B.subtractLocal(result);
		if (ar.dot(br) > 0) { // 小于零，则交点在AB内部
//			System.out.println("交点在AB外部" + min);
			dxy[0] =  min;
		}
//		System.out.println("交点在AB内部" + moreMin);
		dxy[0] =  moreMin;
		return dxy;
	}
}

//
// 最下面是自己用的，上面的把Vector3f这个类删除了些，可以单独使用，不用引入JME3的类
//
// 在这里是用了JME3的Vector3f
//
// package com.innover.util;
//
// import com.jme3.math.Vector3f;
//
// import java.awt.*;
//
// /**
// * author: qifan.yang
// */
// public class NearestPoint {
// private Vector3f A = new Vector3f(0, 0, 0);
// private Vector3f B = new Vector3f(3, 0, 0);
// private Vector3f C = new Vector3f(3f, 3, 0); //直线外一点
//
// public NearestPoint() {
// }
//
// private float calculateDistance(Vector3f A ,Vector3f B,Vector3f C) {
// //计算点到直线的距离
// Vector3f abDir = A.subtract(B).normalize(); //AB
// Vector3f cb = C.subtract(B);
// float length = abDir.dot(cb); //cb在ab上的投影
// Vector3f result = abDir.mult(length).add(B); //计算出垂直交点
//
// System.out.println("ths cross point :" + result);
// float distance_a = C.distance(A);
// float distance_b = C.distance(B);
// float distance_result = C.distance(result);
//
// float min = Math.min(distance_a, distance_b);
// float moreMin = Math.min(min, distance_result);
//
//
// Vector3f ar = A.subtractLocal(result);
// Vector3f br = B.subtractLocal(result);
// if (ar.dot(br) > 0) { //小于零，则交点在AB内部
// System.out.println("交点在AB外部");
// return min;
// }
// System.out.println("交点在AB内部");
// return moreMin;
// }
//
// public static void main(String[] args) {
// NearestPoint nearestPoint = new NearestPoint();
// float ok = nearestPoint.calculateDistance();
// System.out.println("the nearest distance is :" + ok);
// }
//
// }