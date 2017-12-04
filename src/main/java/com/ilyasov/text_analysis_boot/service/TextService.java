package com.ilyasov.text_analysis_boot.service;

import java.util.List;

/**
 * @author damir
 */
public interface TextService {
  List<String> convert(StringBuilder text);

  List<String> convert(String text);
}
