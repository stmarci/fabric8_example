package com.example.fabric8_example.watcher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PodRequestSpec {
    private String podName;
    private String imageName;
    private Integer port;
}
