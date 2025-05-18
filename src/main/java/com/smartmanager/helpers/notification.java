package com.smartmanager.helpers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class notification {
 private String msg;
 @Builder.Default
 private notificationType type= notificationType.red;
}
