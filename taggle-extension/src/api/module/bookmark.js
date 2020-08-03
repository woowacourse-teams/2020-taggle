import ApiService from '../index.js';
import { bookmarks, tagBookmarks, tagMock } from '../../utils/mockData.js';

const BASE_URL = '/api/v1/bookmarks';

let dataId = 1000;

const BookmarkService = {
  find(bookmarkId) {
    return tagBookmarks.find((tagBookmark) => tagBookmark.id === bookmarkId);
    // 실제 API 연동 코드
    //return ApiService.get(`${BASE_URL}/${bookmarkId}/tags`)
  },
  save(newBookmark) {
    const findBookmark = bookmarks.find((bookmark) => bookmark.url === newBookmark.url);
    if (findBookmark) {
      return findBookmark.id;
    }
    const createdId = dataId++;
    const items = {
      id: createdId,
      url: newBookmark.url,
    };
    bookmarks.push(items);
    tagBookmarks.push({ ...items, tags: [] });
    return createdId;
    // 실제 API 연동 코드
    // return ApiService.post(`${BASE_URL}`, newBookmark).then((location) => {
    //   const locations = location.split('/');
    //   return locations[locations.length - 1];
    // });
  },
  delete(id) {
    bookmarks.splice(
      bookmarks.findIndex((bookmark) => bookmark.id === id),
      1,
    );
    tagBookmarks.splice(
      tagBookmarks.findIndex((tagBookmark) => tagBookmark.id === id),
      1,
    );
    // 실제 API 연동 코드
    //ApiService.delete(`${BASE_URL}/${id}`);
  },
};

export default BookmarkService;
