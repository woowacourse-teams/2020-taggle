<template>
  <v-menu bottom :offset-x="true">
    <template v-slot:activator="{ on, attrs }">
      <v-btn icon v-bind="attrs" v-on="on" @click.prevent="">
        <v-icon>mdi-dots-horizontal</v-icon>
      </v-btn>
    </template>

    <v-list>
      <slot name="menuItems" />
    </v-list>
  </v-menu>
</template>

<script>
export default {
  name: 'ContextMenu',
  data() {
    return {
      items: [{ title: '수정' }, { title: '삭제' }],
    };
  },
};
</script>

<style scoped></style>
