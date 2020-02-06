package com.gabriel.stage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Gabriel
 * @date: 2020/2/5 12:34
 * @description 用戶实体类（仅做演示，可以按需补充字段）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    String Id;
    String username;
    String password;
}
