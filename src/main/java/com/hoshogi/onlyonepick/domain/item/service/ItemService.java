package com.hoshogi.onlyonepick.domain.item.service;

import com.hoshogi.onlyonepick.domain.item.entity.Item;

public interface ItemService {

    Item findById(Long itemId);
}
