<template>
  <v-btn icon @click.prevent="openModal">
    <v-icon>mdi-pencil</v-icon>
    <v-dialog v-model="dialog" max-width="500">
      <v-card>
        <v-app-bar dense flat>
          <v-toolbar-title>태그 편집</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn icon @click="closeModal">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-app-bar>
        <v-card-text>
          <vue-tags-input
            v-model="tag"
            :tags="tags"
            @tags-changed="(newTags) => (tags = newTags)"
            placeholder="해당 북마크에 태그를 추가하거나 삭제할 수 있습니다."
          />
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-btn>
</template>

<script>
import VueTagsInput from '@johmun/vue-tags-input';

export default {
  name: 'TagEditModal',
  components: {
    VueTagsInput,
  },
  props: {
    bookmark: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      dialog: false,
      tag: '',
      tags: [],
    };
  },
  methods: {
    closeModal() {
      this.dialog = false;
    },
    openModal() {
      this.dialog = true;
    },
  },
};
</script>

<style scoped></style>
