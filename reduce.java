// Reduce
public static class ReduceRecord extends 
Reducer<Text, IntPair, Text, DoubleWritable> {
    
    public void reduce(Text module, Iterable<IntPair> values, Context context)
    throws IOException, InterruptedException {
        
        Integer sum = 0;
        Integer count = 0;

        // for all values grade, count from list [values]
        for (IntPair value: values) {

            // get first value (grade) and second (count) add it with previous values
            sum = sum + value.getFirstInt();
            count = count + value.getSecondInt();
        }

        // calculate average
        Double averageGrade = (Double) sum / count;
        context.write(module, new DoubleWritable(averageGrade));
    }
}