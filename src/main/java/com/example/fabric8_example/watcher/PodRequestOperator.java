package com.example.fabric8_example.watcher;

import io.fabric8.kubernetes.api.model.KubernetesResourceList;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.fabric8.kubernetes.client.informers.ResourceEventHandler;
import io.fabric8.kubernetes.client.informers.SharedIndexInformer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PodRequestOperator {

    private final KubernetesClient kubernetesClient;

    @PostConstruct
    public void sharedInformer()  {
            MixedOperation<PodRequest, KubernetesResourceList<PodRequest>, Resource<PodRequest>> bookOp = kubernetesClient.resources(PodRequest.class);

            SharedIndexInformer<PodRequest> informer = bookOp.inNamespace("default").inform(new ResourceEventHandler<>() {
                @Override
                public void onAdd(PodRequest podRequest) { log.info("{} ADDED");
                createOrUpdatePod(podRequest);}

                @Override
                public void onUpdate(PodRequest podRequest, PodRequest t1) { log.info("{} UPDATED");
                    createOrUpdatePod(podRequest);}

                @Override
                public void onDelete(PodRequest podRequest, boolean b) { log.info("{} DELETED");
                }
            });

            informer.close();
        }

    private void createOrUpdatePod(PodRequest podRequest) {
        String podName = podRequest.getSpec().getPodName();
        String imageName = podRequest.getSpec().getImageName();
        Integer port = podRequest.getSpec().getPort();

        Pod pod = new PodBuilder()
                .withNewMetadata()
                .withName(podName)
                .endMetadata()
                .withNewSpec()
                .addNewContainer()
                .withName(podName)
                .withImage(imageName)
                .addNewPort()
                .withContainerPort(port != null ? port : 80)
                .endPort()
                .endContainer()
                .endSpec()
                .build();

        kubernetesClient.pods().inNamespace("default").createOrReplace(pod);
    }

}

