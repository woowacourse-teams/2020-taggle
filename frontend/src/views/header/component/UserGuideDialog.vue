<template>
  <v-dialog v-model="userGuideDialog" width="1200">
    <v-card>
      <v-carousel hide-delimiter-background show-arrows-on-hover height="auto">
        <v-carousel-item v-for="(item, i) in items" :key="i" eager>
          <v-img :src="item.src" height="auto" eager></v-img>
        </v-carousel-item>
      </v-carousel>
      <v-card>
        <v-card-actions>
          <v-btn text depressed v-if="permanentClose" @click.prevent="onClickPermanentClose">다시 보지 않기</v-btn>
          <v-spacer />
          <v-btn text depressed @click.prevent="onClickClose">닫기</v-btn>
        </v-card-actions>
      </v-card>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: 'UserGuideModal',
  props: {
    userGuideDialog: {
      type: Boolean,
      required: true,
    },
    permanentClose: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      items: [
        {
          src: 'https://i.ibb.co/gP4rhRS/guide-1-bookmark-add.png',
        },
        {
          src: 'https://i.ibb.co/8m4Z28Q/guide-2-bookmark-manage.png',
        },
        {
          src: 'https://i.ibb.co/wC9bXDL/guide-3-tag-category-manage.png',
        },
        {
          src: 'https://i.ibb.co/T4QZkQn/guide-4-tag-search.png',
        },
        {
          src: 'https://i.ibb.co/f8SkQ5k/guide-5-chrome-extension.png',
        },
      ],
    };
  },
  methods: {
    onClickClose() {
      this.$emit('close');
    },
    onClickPermanentClose() {
      localStorage.setItem('dont_look_again', 'true');
      this.$emit('close');
    },
  },
};
</script>
