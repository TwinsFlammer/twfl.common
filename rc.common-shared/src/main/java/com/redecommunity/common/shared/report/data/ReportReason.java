package com.redecommunity.common.shared.report.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
@Getter
public class ReportReason {
    private final Integer id;
    private final String name, displayName, description;
}
