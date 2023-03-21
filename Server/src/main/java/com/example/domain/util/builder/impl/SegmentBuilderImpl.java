package com.example.domain.util.builder.impl;

import com.example.domain.util.builder.SegmentBuilder;
import entity.Segment;

public class SegmentBuilderImpl implements SegmentBuilder {

    private String segmentId;
    private String segmentName;

    public SegmentBuilderImpl() {
        this.segmentId = "";
    }


    public SegmentBuilderImpl(String segmentId) {
        this.segmentId = segmentId;
    }

    @Override
    public SegmentBuilder withName(String name) {
        this.segmentName = name;
        return this;
    }

    @Override
    public Segment build() {
        Segment segment = new Segment(segmentId);
        segment.setName(segmentName);
        return segment;
    }
}
