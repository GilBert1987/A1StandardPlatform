package com.common.iface;

import java.util.List;

public interface IGroupByUser {
	List<?> findGroupsByUser(String userId);
}
