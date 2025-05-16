#!/bin/bash

# Configuration
SRC_DIR="src"
OUT_DIR="bin"
INPUT_DIR="input"
OUTPUT_BASE="output"

# Step 1: Compile Java source code
echo "Compiling Java sources..."
mkdir -p "$OUT_DIR"
find "$SRC_DIR" -name "*.java" > sources.txt
javac -d "$OUT_DIR" @sources.txt
if [ $? -ne 0 ]; then
    echo "Compilation failed!"
    exit 1
fi
rm sources.txt

# Step 2: Run each test
echo "Running simulations..."
for input_file in "$INPUT_DIR"/*.txt; do
    filename=$(basename "$input_file" .txt)
    output_folder="$OUTPUT_BASE/$filename"
    mkdir -p "$output_folder"

    echo "Running test case: $filename"
    java -cp "$OUT_DIR" app.App "$input_file" "$output_folder"
done

echo "All test cases completed."
