import { tagBookmarks, tagMock } from '../../utils/mockData.js';

const BASE_URL = '/api/v1/tags';

let id = 1000;

const TagService = {
  createTag(newTag) {
    console.log(newTag);
    const findTag = tagMock.find((tag) => tag.name === newTag.name);
    if (findTag) {
      return findTag.id;
    }
    const createdId = id++;
    tagMock.push({
      id: createdId,
      name: newTag.name,
    });
    return createdId;

    // 실제 API 연동 코드
    // return ApiService.post(`${BASE_URL}`, newTag).then((location) => {
    //   const locations = location.split('/');
    //   return locations[locations.length - 1];
    // });
  },
  addBookmarkOnTag(bookmarkId, tagId) {
    const tagBookmarkIndex = tagBookmarks.findIndex((tagBookmark) => tagBookmark.id === bookmarkId);
    const tagBookmark = tagBookmarks[tagBookmarkIndex];
    const tag = tagMock.find((tag) => tag.id === tagId);
    tagBookmark.tags.push(tag);
    // 실제 API 연동 코드
    //ApiService.post(`${BASE_URL}/${tagId}/bookmarks/${bookmarkId}`);
  },
  removeBookmarkOnTag(bookmarkId, tagId) {
    const tagBookmarkIndex = tagBookmarks.findIndex((tagBookmark) => tagBookmark.id === bookmarkId);
    const tagBookmark = tagBookmarks[tagBookmarkIndex];
    tagBookmark.tags.splice(
      tagBookmark.tags.findIndex((tag) => tag.id === tagId),
      1,
    );
    // 실제 API 연동 코드
    //ApiService.delete(`${BASE_URL}/${tagId}/bookmarks/${bookmarkId}`);
  },
};

export default TagService;
