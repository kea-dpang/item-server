package kea.dpang.item.converter;

import kea.dpang.item.entity.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCategoryConverter implements Converter<String, Category> {

    @Override
    public Category convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        try {
            return Category.valueOf(source);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
