// Map record (In mapper combiner)
public static class MapRecord extends 
Mapper<LongWritable, Text, Text, IntPair> {

    // hashMap to store module and grades
    Map<String, Integer> moduleGradesDict = new HashMap();

    // hashMap to store module and module count
    Map<String, Integer> countDict = new HashMap();

    public void map(LongWritable key, Text value, Context context) 
    throws IOException, InterruptedException {

        // no need to loop through framework itself take care calculation
        // split row by comma
        String[] row = value.toString().split(",");

        // get module name and convert it into lowercase
        String module = row[4].toLowerCase();

        // get module grade and convert it into Integer
        Integer grade = new Integer(row[5]);

        // if moduleGradesDict already contains key, add grade with previous grade
        If (moduleGradesDict.containsKey(module)) {

            Integer total = (Integer) moduleGradesDict.get(module) + grade;
            moduleGradesDict.put(module, grade);

        } else {

            // else put module and create new list
            moduleGradesDict.put(module, grade);
        }

        // if countDict already contain module add count +1 to the previous count
        if (countDict.containsKey(module)) {

            Integer count = (Integer) countDict.get(module) + 1;
            countDict.put(module, count);

        } else {

            // else put module and count (1)
            countDict.put(module, 1);
        }

        // cleanup
        protected void cleanup(Context context) throws IOException, InterruptedException {

            Iterator<Map.Entry<String, Integer>> it = moduleGradesdict.entrySet().iterator();

            while (it.hasNext()) {

                Entry<String, Integer> entry = it.next();

                // get module name and module grade
                String keyModule = entry.getKey();
                Integer valueGrade = entry.getValue().intValue();

                // get module count
                Integer valueCount = (Integer) countDict.get(keyModule);
                
                // set each module and module grade with count as key, value pair
                context.write(new Text(keyMoodle), new IntPair(valueGrade, valueCount));

            }
        }
    }
}