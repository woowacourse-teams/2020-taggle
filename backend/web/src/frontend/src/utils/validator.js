const validator = {
  bookmark: {
    url: [
      (v) => !!v || 'URL값은 필수입니다.',
      (v) =>
        /^(https?):\/\/[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]$/.test(v) || 'URL은 유효한 형태여야 합니다.',
    ],
  },
  category: {
    title: [
      (v) => !!v || '빈값일 수 없습니다.',
      (v) => v.trim().length > 0 || '공백문자 외 값 필수',
      (v) => v.length <= 20 || '20자보다 길수 없음.',
    ],
    changeTitleFromTag: [(v) => !!v || '변경할 카테고리를 지정해주세요.'],
  },
};

export default validator;
