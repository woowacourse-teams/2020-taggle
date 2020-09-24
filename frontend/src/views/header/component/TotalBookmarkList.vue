<template>
  <v-list class="grow">
    <v-list-item>
      <v-list-item-icon class="mx-0">
        <v-icon>dehaze</v-icon>
      </v-list-item-icon>
      <v-list-item class="font-weight-black text-h7" @click="push">
        All
      </v-list-item>
    </v-list-item>
  </v-list>
</template>

<script>
export default {
  methods: {
    push() {
      this.$router.push({ name: 'bookmarks' }).catch(() => {});
    },
  },
};
</script>
