package com.cyber.portal.sharedResources.util;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class NLPModelTrainer {

    public static void main(String[] args) throws Exception {

        DataSource source = new DataSource("complaints.arff");
        Instances dataset = source.getDataSet();
        dataset.setClassIndex(dataset.numAttributes() - 1);

        StringToWordVector filter = new StringToWordVector();
        filter.setTFTransform(true);
        filter.setIDFTransform(true);
        filter.setLowerCaseTokens(true);

        NaiveBayes nb = new NaiveBayes();

        FilteredClassifier fc = new FilteredClassifier();
        fc.setFilter(filter);
        fc.setClassifier(nb);

        fc.buildClassifier(dataset);

        weka.core.SerializationHelper.write("complaint.model", fc);

        System.out.println("Model trained and saved.");
    }
}
