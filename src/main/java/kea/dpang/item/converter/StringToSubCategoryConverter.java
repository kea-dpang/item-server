package kea.dpang.item.converter;

import kea.dpang.item.entity.SubCategory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSubCategoryConverter implements Converter<String, SubCategory> {

    @Override
    public SubCategory convert(String source) {
        if (source == null || source.isEmpty()) {
            return SubCategory.NONE;
        }
        try {
            return SubCategory.valueOf(source);
        } catch (IllegalArgumentException e) {
            return SubCategory.NONE;
        }
    }
}
