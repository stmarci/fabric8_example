package com.example.fabric8_example.controller;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class PodController {

    private final KubernetesClient kubernetesClient;

    @PostMapping("/createPod")
    public String createPod() {
        try {
            Pod pod = new PodBuilder()
                    .withNewMetadata()
                    .withName("example-pod")
                    .endMetadata()
                    .withNewSpec()
                    .addNewContainer()
                    .withName("nginx")
                    .withImage("nginx:1.14.2")
                    .addNewPort()
                    .withContainerPort(80)
                    .endPort()
                    .endContainer()
                    .endSpec()
                    .build();

            kubernetesClient.pods().inNamespace("default").resource(pod).create();
            return "Pod created successfully";
        } catch (KubernetesClientException e) {
            log.error("Failed to create pod", e);
            return "Failed to create pod: " + e.getMessage();
        }
    }
}

