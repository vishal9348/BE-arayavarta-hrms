package com.aryavarta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.aryavarta")
public class HrmsApplication {
  public static void main(String[] args) { SpringApplication.run(HrmsApplication.class, args); }
}
