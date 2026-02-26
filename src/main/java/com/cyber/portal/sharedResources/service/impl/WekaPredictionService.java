package com.cyber.portal.sharedResources.service.impl;

import com.cyber.portal.sharedResources.enums.Label;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.DenseInstance;
import weka.core.Instances;

@Service
@RequiredArgsConstructor
@Slf4j
public class WekaPredictionService {

    private final FilteredClassifier model;

    public Label predict(String description) {

        try {
            Instances structure = model.getFilter().getCopyOfInputFormat();

            Instances data = new Instances(structure);
            data.setClassIndex(data.numAttributes() - 1);

            DenseInstance instance = new DenseInstance(data.numAttributes());
            instance.setDataset(data);

            instance.setValue(0, description);

            data.add(instance);

            double prediction = model.classifyInstance(data.firstInstance());

            String result =
                    data.classAttribute().value((int) prediction);

            log.info("Weka Prediction: {}", result);

            return Label.valueOf(result);

        } catch (Exception e) {
            throw new RuntimeException("Prediction failed", e);
        }
    }
}
