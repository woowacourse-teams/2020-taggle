// eslint-disable-next-line import/prefer-default-export
export const MESSAGES = {
  TAG: {
    ADD: {
      FAIL: '태그 저장 중 오류 발생',
    },
    EDIT: {
      SUCCESS: '태그의 카테고리 변경 성공',
      FAIL: '태그의 카테고리 변경 중 오류 발생',
    },
    DELETE: {
      SUCCESS: '태그 제거 성공',
      FAIL: '태그 제거 중 오류 발생',
    },
  },
  BOOKMARK: {
    FETCH: {
      FAIL: '북마크에 등록된 태그목록 로딩중 오류 발생',
    },
    ADD: {
      SUCCESS: '북마크 저장 성공',
      FAIL: '북마크 저장 중 오류 발생',
    },
    DELETE: {
      SUCCESS: '북마크 제거 성공',
      FAIL: '북마크 제거 중 오류 발생',
    },
  },
  TAG_WITH_BOOKMARKS: {
    ADD: {
      SUCCESS: '북마크에 태그 추가 성공',
      FAIL: '북마크에 태그 추가 중 오류 발생',
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
  CATEGORY: {
    ADD: {
      SUCCESS: '카테고리 저장 성공',
      FAIL: '카테고리 저장 중 오류 발생',
    },
    EDIT: {
      SUCCESS: '카테고리명 수정 성공',
      FAIL: '카테고리명 수정 중 오류 발생',
    },
    DELETE: {
      SUCCESS: '카테고리 제거 성공',
      FAIL: '카테고리 제거 중 오류 발생',
    },
  },
};
