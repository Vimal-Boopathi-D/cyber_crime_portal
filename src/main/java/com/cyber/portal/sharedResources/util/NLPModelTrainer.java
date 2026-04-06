package com.cyber.portal.sharedResources.util;

import weka.classifiers.functions.SMO;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.tokenizers.NGramTokenizer;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class NLPModelTrainer {

    private static final String DATASET_PATH = "complaints.arff";
    private static final String MODEL_PATH = "complaint.model";

    public static void main(String[] args) throws Exception {

        DataSource source = new DataSource(DATASET_PATH);
        Instances dataset = source.getDataSet();

        if (dataset.classIndex() == -1) {
            dataset.setClassIndex(dataset.numAttributes() - 1);
        }

        StringToWordVector filter = new StringToWordVector();

        filter.setTFTransform(true);
        filter.setIDFTransform(true);
        filter.setLowerCaseTokens(true);

        NGramTokenizer tokenizer = new NGramTokenizer();
        tokenizer.setNGramMinSize(1);
        tokenizer.setNGramMaxSize(2);

        filter.setTokenizer(tokenizer);
        filter.setWordsToKeep(5000);
        filter.setStopwordsHandler(new weka.core.stopwords.Rainbow());

        SMO svm = new SMO();

        FilteredClassifier fc = new FilteredClassifier();
        fc.setFilter(filter);
        fc.setClassifier(svm);

        fc.buildClassifier(dataset);

        weka.core.SerializationHelper.write(MODEL_PATH, fc);

        System.out.println(" Model trained and saved at: " + MODEL_PATH);
    }
}