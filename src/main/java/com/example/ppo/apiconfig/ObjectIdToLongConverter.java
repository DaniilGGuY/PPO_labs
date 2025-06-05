package com.example.ppo.apiconfig;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.bson.types.ObjectId;

@ReadingConverter
public class ObjectIdToLongConverter implements Converter<ObjectId, Long> {
    @Override
    public Long convert(ObjectId source) {
        String hex = source.toHexString().substring(0, 16);
        return Long.parseUnsignedLong(hex, 16);
    }
}