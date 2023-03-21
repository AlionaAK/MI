package com.example.domain.util.builder;


import entity.Segment;

public interface SegmentBuilder {

    SegmentBuilder withName(String name);

    Segment build();
}
