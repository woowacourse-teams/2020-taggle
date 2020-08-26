<template>
  <v-dialog v-model="dialog" width="500">
    <v-card>
      <v-app-bar dense flat>
        <v-card-title>카테고리 변경</v-card-title>
        <v-spacer></v-spacer>
        <v-btn icon @click="dialog = false">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-app-bar>

      <v-card-text class="pt-6">
        변경내용
        <!--        <v-form ref="category" @submit.prevent="onEditCategory">-->
        <!--          <v-text-field-->
        <!--            v-model="category.title"-->
        <!--            dense-->
        <!--            :rules="rules"-->
        <!--            label="이름을 입력후에 enter를 입력하여 저장."-->
        <!--          ></v-text-field>-->
        <!--        </v-form>-->
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: 'CategoryEditModal',
  data() {
    return {
      dialog: false,
    };
  },
  props: {
    tag: {
      type: Object,
      required: true,
    },
    editDialog: {
      type: Boolean,
      required: true,
    },
  },
  watch: {
    editDialog(value) {
      if (value) {
        this.dialog = value;
      }
    },
    dialog() {
      if (!this.dialog) {
        this.$emit('close');
      }
    },
  },
};
</script>
