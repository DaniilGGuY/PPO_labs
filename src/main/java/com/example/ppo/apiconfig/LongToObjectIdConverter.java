package com.example.ppo.apiconfig;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.bson.types.ObjectId;

@WritingConverter
public class LongToObjectIdConverter implements Converter<Long, ObjectId> {
    @Override
    public ObjectId convert(Long source) {
        String hexString = String.format("%024x", source);
        return new ObjectId(hexString);
    }
}