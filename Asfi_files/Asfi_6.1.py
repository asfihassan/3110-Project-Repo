
A = [
    [1, 2, 3],
    [4, 5, 6]
]

B = [
    [7, 8],
    [9, 10],
    [11, 12]
]

rows_A = len(A)
cols_A = len(A[0])
rows_B = len(B)
cols_B = len(B[0])

# Ensure the matrices can be multiplied
if cols_A != rows_B:
    raise ValueError("Matrix dimensions do not match for multiplication")

# Initialize result matrix with zeros
result = [[0 for _ in range(cols_B)] for _ in range(rows_A)]

# Perform matrix multiplication
for i in range(rows_A):
    for j in range(cols_B):
        for k in range(cols_A):      # or rows_B, same value
            result[i][j] += A[i][k] * B[k][j]

# Print the matrices and the result
print("Matrix A:")
for row in A:
    print(row)

print("\nMatrix B:")
for row in B:
    print(row)

print("\nResult (A × B):")
for row in result:
    print(row)
