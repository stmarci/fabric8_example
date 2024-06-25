package com.example.fabric8_example.watcher;
import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Group("example.com")
@Version("v1")
public class PodRequest extends CustomResource<PodRequestSpec, PodRequestStatus> implements Namespaced {
    private PodRequestSpec spec;
}
