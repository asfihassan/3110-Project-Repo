

def pretty_print(mat, name="Matrix"):
    print(f"\n{name}:")
    for row in mat:
        print(" ", row)

def multiply_matrices(A, B):
    rows_A, cols_A = len(A), len(A[0])
    rows_B, cols_B = len(B), len(B[0])

    if cols_A != rows_B:
        raise ValueError("Matrix dimensions do not match for multiplication")

    result = [[0 for _ in range(cols_B)] for _ in range(rows_A)]

    # Perform multiplication
    for i in range(rows_A):
        for j in range(cols_B):
            for k in range(cols_A):
                result[i][j] += A[i][k] * B[k][j]
    return result

A = [
    [2, 1],
    [0, 3],
    [4, 5]
]

B = [
    [1, 2, 3],
    [4, 5, 6]
]

# Multiply & store the result
result = multiply_matrices(A, B)

print("Matrices multiplied and results stored successfully.")

pretty_print(A, "A")
pretty_print(B, "B")
pretty_print(result, "A × B (Result)")
