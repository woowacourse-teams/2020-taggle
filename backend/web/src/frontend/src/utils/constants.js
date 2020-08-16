// eslint-disable-next-line import/prefer-default-export
export const MESSAGES = {
  TAG: {
    ADD: {
      FAIL: '태그 추가 중 오류 발생',
    },
    DELETE: {
      SUCCESS: '태그 제거됨',
      FAIL: '태그 제거 중 오류 발생',
    },
  },
  BOOKMARK: {
    ADD: {
      SUCCESS: '북마크 저장됨',
      FAIL: '북마크 저장 중 오류 발생',
    },
    DELETE: {
      SUCCESS: '북마크 제거됨',
      FAIL: '북마크 제거 중 오류 발생',
    },
  },
  TAG_WITH_BOOKMARKS: {
    ADD: {
      SUCCESS: '북마크에 태그 저장됨',
      FAIL: '북마크에 태그 저장 중 오류 발생',
    },
    DELETE: {
      SUCCESS: '북마크에 태그 제거됨',
      FAIL: '북마크에 태그 제거 중 오류 발생',
    },
  },
  BOOKMARK_WITH_TAGS: {
    FETCH: {
      FAIL: '북마크에 등록된 태그목록 로딩중 오류 발생',
    },
  },
};
