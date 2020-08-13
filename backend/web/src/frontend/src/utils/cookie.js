// eslint-disable-next-line consistent-return
const getCookie = (name) => {
  // eslint-disable-next-line no-plusplus
  for (let cookies = document.cookie.split(';'), i = 0; i < cookies.length; i++) {
    if (cookies[i].indexOf('=') === -1) {
      if (name === cookies[i]) {
        return '';
      }
    } else {
      const crumb = cookies[i].split('=');
      if (name === crumb[0].trim()) {
        return unescape(crumb[1].trim());
      }
    }
  }
};

const cookie = {
  getCookie,
};

export default cookie;
