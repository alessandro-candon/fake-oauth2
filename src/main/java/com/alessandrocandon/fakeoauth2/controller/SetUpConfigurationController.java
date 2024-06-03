/* Decathlon Italy - Tacos Team(C) 2024 */
package com.alessandrocandon.fakeoauth2.controller;

import com.alessandrocandon.fakeoauth2.dto.SetUpConfigurationDto;
import com.alessandrocandon.fakeoauth2.service.SetUpConfigurationService;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
public class SetUpConfigurationController {

  @PostMapping("/configurations")
  public void post(@RequestBody SetUpConfigurationDto setUpConfigurationDto) {
    SetUpConfigurationService.setConfiguration(setUpConfigurationDto);
  }

  @GetMapping("/configurations/{key}")
  public SetUpConfigurationDto get(@PathVariable Integer key) {
    return SetUpConfigurationService.getConfiguration(key);
  }

  @GetMapping("/configurations")
  public Map<Integer, SetUpConfigurationDto> getAll() {
    return SetUpConfigurationService.getAllConfigurations();
  }

  @DeleteMapping("/configurations/{key}")
  public void resetUsers(@PathVariable Integer key) {
    SetUpConfigurationService.deleteConfiguration(key);
  }

  @PostMapping("/well-known")
  public void postWellKnown(@RequestBody JsonNode wellKnown) {
    SetUpConfigurationService.setOAuthWellKnow(wellKnown);
  }
}
