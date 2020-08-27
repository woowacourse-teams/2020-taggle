const validator = {
  bookmark: {
    url: [
      (v) => !!v || 'URL값은 필수입니다.',
      (v) =>
        /^(https?):\/\/[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]$/.test(v) || 'URL은 유효한 형태여야 합니다.',
    ],
  },
  category: {
    title: [(v) => !!v || 'title값은 필수입니다.'],
  },
};

export default validator;
