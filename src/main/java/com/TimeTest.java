package com;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class TimeTest {

  public static void main(String[] args) {

    // 假设有一个 OffsetDateTime 对象
    OffsetDateTime offsetDateTime = OffsetDateTime.now();
    System.out.println("Original OffsetDateTime: " + offsetDateTime);

    // 保持时间值不变，使用 ZoneId 设置新的偏移量
    ZoneId newZoneId = ZoneId.of("America/New_York"); // 选择你需要的时区
    ZoneOffset newOffset = newZoneId.getRules().getOffset(offsetDateTime.toLocalDateTime());

    OffsetDateTime newOffsetDateTime = offsetDateTime.withOffsetSameLocal(newOffset);
    System.out.println("Modified OffsetDateTime with new offset: " + newOffsetDateTime);

    // 保持实际时间不变，更改偏移量。
    newOffset = newZoneId.getRules().getOffset(offsetDateTime.toInstant());
    newOffsetDateTime = offsetDateTime.withOffsetSameInstant(newOffset);
    System.out.println("Same OffsetDateTime with new offset: " + newOffsetDateTime);

    ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(1900, 1, 1, 0, 0, 0),
        ZoneId.of("Pacific/Auckland"));
    System.out.println(zonedDateTime.toOffsetDateTime());
  }
  


}
