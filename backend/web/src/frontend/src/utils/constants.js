// eslint-disable-next-line import/prefer-default-export
export const MESSAGES = {
  TAG: {
    ADD: {
      FAIL: '태그 추가 중 오류 발생',
    },
    EDIT: {
      SUCCESS: '태그를 카테고리에 옮겼습니다.',
      FAIL: '태그를 카테고리에 옮기는 중에 오류 발생',
    },
    DELETE: {
      SUCCESS: '태그 제거됨',
      FAIL: '태그 제거 중 오류 발생',
    },
  },
  BOOKMARK: {
    FETCH: {
      FAIL: '북마크에 등록된 태그목록 로딩중 오류 발생',
    },
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
  CATEGORY: {
    ADD: {
      SUCCESS: '카테고리가 등록되었습니다.',
      FAIL: '카테고리 등록 중 오류가 발생했습니다.',
    },
    EDIT: {
      SUCCESS: '카테고리가 수정되었습니다.',
      FAIL: '카테고리 수정 중 오류가 발생했습니다.',
    },
    DELETE: {
      SUCCESS: '카테고리가 제거되었습니다.',
      FAIL: '카테고리 삭제 중 오류가 발생했습니다.',
    },
  },
  USER: {
    NOTIFICATION_EMAIL: {
      SUCCESS: '알람 이메일 수정됨',
      FAIL: '알람 이메일 수정 오류 발생',
    },
    NOTIFICATION_ENABLED: {
      FAIL: '알람 수정 오류 발생',
    },
    DELETE: {
      SUCCESS: '회원 탈퇴 성공',
      FAIL: '회원 탈퇴 오류 발생',
    },
  },
};
