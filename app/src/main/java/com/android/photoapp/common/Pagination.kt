package com.android.photoapp.common

class Pagination(internal val itemsPerPage: Int = ITEMS_PER_PAGE_30) {
    private var currentPage: Int = 1
    private var totalItems: Int = 0

    fun canLoadMore(size: Int): Boolean {
        return size < totalItems
    }

    internal fun setTotal(value: Int) {
        totalItems = value
    }

    fun reset() {
        currentPage = 1
        totalItems = 0
    }

    internal fun pageLoaded(addPage: Boolean = false) {
        if (totalItems != 0) {
            if (addPage)
                currentPage--
            else
                currentPage++
        }
    }

    internal fun nextPage(): Int {
        return if (totalItems == 0) 1 else currentPage
    }

    internal fun previousPage(): Int {
        return if (totalItems == 0) 0 else if (currentPage == 0) 0 else currentPage - 1
    }

    fun getCurrentPage(): Int {
        return currentPage
    }

    fun getTotalCount(): Int {
        return totalItems
    }

    fun canLoad(isFirstLoaded: Boolean, size: Int): Boolean {
        return if (isFirstLoaded) {
            reset()
            true
        } else {
            canLoadMore(size)
        }
    }

    companion object {
        const val DEFAULT_ITEMS_PER_PAGE = 10
        const val ITEMS_PER_PAGE_30 = 30
    }
}