package com.isea533.mybatis.mapper;

import java.util.List;

import com.isea533.mybatis.model.Country;

import tk.mybatis.mapper.common.Mapper;

public interface CountryMapper extends Mapper<Country> {

	List<Country> selectAllByPage();

}