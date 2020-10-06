// eslint-disable-next-line import/prefer-default-export
export const SNACKBAR_MESSAGES = {
  BOOKMARK: {
    ADD: {
      FAIL: '북마크 추가 과정에 오류가 발생했습니다.',
    },
    DELETE: {
      FAIL: '북마크 항목을 삭제중 과정에 오류가 발생했습니다.',
    },
  },
  TAG_BOOKMARK: {
    FETCH: {
      FAIL: '태그 북마크 로딩 과정 중 오류가 발생했습니다.',
    },
    ADD: {
      SUCCESS: '북마크에 태그 추가 성공',
      FAIL: '태그 북마크 추가 과정 중 오류가 발생했습니다.',
    },
    DELETE: {
      SUCCESS: '북마크에 태그 제거됨',
      FAIL: '태그 북마크 삭제 과정 중 오류가 발생했습니다.',
    },
  },
  BOOKMARK_WITH_TAGS: {
    FETCH: {
      FAIL: '북마크에 등록된 태그목록 로딩중 오류 발생',
    },
  },
};

export const SERVICE_URL = 'https://taggle.kr';
