/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.isea533.mybatis.test.page;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.isea533.mybatis.mapper.CountryMapper;
import com.isea533.mybatis.model.Country;
import com.isea533.mybatis.test.BasicTest;
import com.isea533.mybatis.test.PageQueryHelper;
import com.isea533.mybatis.test.PageQueryHelper.NoPageQuerier;
import com.isea533.mybatis.test.Pagination;

public class PageQueryTest extends BasicTest {

	@Autowired
	private SqlSession sqlSession;

	@Test
	public void testPageQuery() {
		final CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);

		Pagination<Country> page = PageQueryHelper.doPageQuery(3, 10, new NoPageQuerier<Country>() {
			@Override
			public List<Country> query() {
				return countryMapper.selectAllByPage();
			}
		});

		System.out.println(page.getTotal());
		System.out.println(page.getList().size());
	}

}
