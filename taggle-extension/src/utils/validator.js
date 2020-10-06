const validator = {
  tag: {
    name: [
      (v) => {
        if (!v[v.length - 1]) {
          return '빈값일 수 없습니다.';
        }
        if (!v[v.length - 1].trim().length > 0) {
          return '공백문자 외 값 필수입니다.';
        }
        return v[v.length - 1].length <= 25 || '25자보다 길 수 없습니다.';
      },
    ],
  },
};

export default validator;
