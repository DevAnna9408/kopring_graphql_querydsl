package kr.co.anna.domain.converter

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class ListToPageConverter {
    companion object {
        fun <E> pageFromListAndPageable(
            list: List<E>,
            pageable: Pageable,
        ): Page<E> {
            val start = pageable.offset.toInt()
            val end = (start + pageable.pageSize).coerceAtMost(list.size)
            return PageImpl(list.subList(start, end), pageable, list.size.toLong())
        }
    }
}
